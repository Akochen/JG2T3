package LBMS;

import java.sql.*;
import java.time.Instant;

/**
 * Contains methods for manipulating the entire collection of reservations in the database (with JDBC).
 */
public class ReservationCollectionJDBC
{
    /**
     * JDBC Driver.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * Database URL.
     */
    private static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?useSSL=false";
    /**
     * Query statement.
     */
    private static Statement statement = null;
    /**
     * Manages results.
     */
    private static ResultSet resultSet = null;
    /**
     * Manages connection.
     */
    private static Connection connection = null;
    
    ReservationCollectionJDBC() throws ClassNotFoundException, SQLException
    {
        //load the driver class
        Class.forName(DRIVER);
        //establish connection to database
        connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
        //create Statement for querying database
        statement = connection.createStatement();
        //enable reservationCollection methods
        new ReservationCollection();
    }
    
    
    /**
     * Takes reservation attributes, then creates a reservation
     * based on those attributes. Then adds that reservation
     * to the database.
     *
     * @param newUserId       ID of the user account that the reservation is under.
     * @param newRentableId   ID of the rentable that is reserved.
     * @param newRentableType Type of the rentable that is reserved.
     * @return Created reservation.
     * @throws SQLException Executes SQL database query.
     */
    public static Reservation createReservation(String newRentableId, String newUserId, String newRentableType) throws SQLException
    {
        Reservation newReservation = null;
        String roomNumber;
        PreparedStatement ps;
        
        switch (newRentableType)
        {
            case "ITEM":
                //instantiate new reservation
                newReservation = new Reservation(newRentableId, newUserId, newRentableType);
                //add reservation to database
                ps = connection.prepareStatement("INSERT INTO reservation " +
                        "(reservationId, rentableId, userId, reservationDate, " +
                        "reservationExpireDate, isActive, reservationType) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)");
                ps.setObject(1, newReservation.getReservationId());
                ps.setObject(2, newReservation.getRentableId());
                ps.setObject(3, newReservation.getUserId());
                ps.setObject(4, newReservation.getReservationDate());
                ps.setObject(5, newReservation.getReservationExpireDate());
                ps.setObject(6, newReservation.getActive());
                ps.setObject(7, newReservation.getReservationType());
                ps.execute();
                break;
            
            case "ROOM":
                //check if user is already renting a room (one per account)
                PreparedStatement psRoomCheck = connection.prepareStatement("SELECT * " +
                        "FROM reservation " +
                        "WHERE userId = ? " +
                        "AND reservationType LIKE 'ROOM' " +
                        "AND isActive = TRUE");
                psRoomCheck.setObject(1, newUserId);
                resultSet = psRoomCheck.executeQuery();
                
                boolean hasRoomAlready = false;
                if (resultSet.next())
                {
                    hasRoomAlready = true;
                }
                
                if (!hasRoomAlready)
                {
                    resultSet = statement.executeQuery("SELECT * FROM room WHERE isReserved = FALSE");
                    if (resultSet.next())
                    {
                        roomNumber = resultSet.getString("roomNumber");
                    } else
                    {
                        roomNumber = "";
                    }
                    
                    ps = connection.prepareStatement("UPDATE room SET isReserved = TRUE WHERE roomNumber = ?");
                    ps.setObject(1, roomNumber);
                    ps.execute();
                    
                    if (!roomNumber.equals(""))
                    {
                        newReservation = new Reservation(roomNumber, newUserId, newRentableType);
                        //add reservation to database
                        ps = connection.prepareStatement("INSERT INTO reservation " +
                                "(reservationId, rentableId, userId, reservationDate, " +
                                "reservationExpireDate, isActive, reservationType) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)");
                        ps.setObject(1, newReservation.getReservationId());
                        ps.setObject(2, newReservation.getRentableId());
                        ps.setObject(3, newReservation.getUserId());
                        ps.setObject(4, newReservation.getReservationDate());
                        ps.setObject(5, newReservation.getReservationExpireDate());
                        ps.setObject(6, newReservation.getActive());
                        ps.setObject(7, newReservation.getReservationType());
                        ps.execute();
                    }
                }
                //else inform user that no room reservation was created.
                break;
        }
        
        return newReservation;
    }
    
    /**
     * Searches for a reservation ID in the database and, if found,
     * deletes the reservation with that reservation ID. Else, return false.
     *
     * @param idToDelete Reservation ID to search for and delete.
     * @return True if reservation was deleted; false if not
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean deleteReservation(String idToDelete) throws SQLException
    {
        if (searchByReservationId(idToDelete))
        {
            PreparedStatement ps1 = connection.prepareStatement("SELECT * " +
                    "FROM reservation " +
                    "WHERE reservationId = ?");
            ps1.setObject(1, idToDelete);
            resultSet = ps1.executeQuery();
            resultSet.next();
            
            if (resultSet.getString("reservationType").equals("ROOM"))
            {
                PreparedStatement ps2 = connection.prepareStatement("SELECT * " +
                        "FROM room " +
                        "WHERE roomNumber = ?");
                ps2.setObject(1, resultSet.getString("rentableId"));
                resultSet = ps2.executeQuery();
                resultSet.next();
                
                PreparedStatement ps3 = connection.prepareStatement("UPDATE room " +
                        "SET isReserved = FALSE " +
                        "WHERE roomNumber = ?");
                ps3.setObject(1, resultSet.getString("roomNumber"));
                ps3.execute();
            }
            
            PreparedStatement ps4 = connection.prepareStatement("DELETE FROM reservation WHERE reservationId = ?");
            ps4.setObject(1, idToDelete);
            ps4.execute();
            return true;//true if deletion executed
        }
        return false;//false if no deletion executed
    }
    
    /**
     * Builds a formatted string containing all the data of reservations in the database.
     * Is able to show a more narrow viewing of reservations based on the given 'selection' and 'idToView'.
     *
     * @param rentableIdToView If selection is '2' or '3', builds a string according to this given user/rentable ID.
     * @param userIdToView     If selection is '2' or '3', builds a string according to this given user/rentable ID.
     * @param selection        '2' views by user ID, '3' views by rentable ID, and any
     *                         other number views all reservations regardless of ID.
     * @return String of all reservations made in association with the given ID.
     * @throws SQLException Executes SQL database query.
     */
    public static String viewReservations(String reservationIdToView, String rentableIdToView, String userIdToView, String selection) throws SQLException
    {
        updateAllActiveStatus();
        
        StringBuilder reservationListConcat = new StringBuilder();
        
        int numberOfColumns = 0;
        ResultSetMetaData metaData = null;
        
        switch (selection)
        {
            case "1":
                resultSet = statement.executeQuery("SELECT * FROM reservation");
                metaData = resultSet.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;
            
            case "2":
                PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT * FROM reservation WHERE reservationId = ?");
                preparedStatement3.setObject(1, reservationIdToView);
                resultSet = preparedStatement3.executeQuery();
                
                metaData = preparedStatement3.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;
            
            case "3":
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM reservation WHERE userId = ?");
                preparedStatement1.setObject(1, userIdToView);
                resultSet = preparedStatement1.executeQuery();
                
                metaData = preparedStatement1.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;
            
            case "4":
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM reservation WHERE rentableId = ?");
                preparedStatement2.setObject(1, rentableIdToView);
                resultSet = preparedStatement2.executeQuery();
                
                metaData = preparedStatement2.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;
        }
        
        while (resultSet.next())
        {
            reservationListConcat.append('{');
            for (int i = 1; i <= numberOfColumns; i++)
            {
                reservationListConcat.append(metaData.getColumnName(i)).append('=').
                        append(resultSet.getObject(i));
                if (i == numberOfColumns)
                {
                    reservationListConcat.append('}');
                } else
                {
                    reservationListConcat.append(",\t");
                }
            }
            reservationListConcat.append("\n");
        }
        
        return reservationListConcat.toString();
    }
    
    /**
     * Takes a reservation ID of a reservation in the database that the logged in user has access to,
     * then cancels (calls deleteReservation on) the reservation.
     *
     * @param reservationIdToCancel Reservation ID to cancel.
     * @param userIdLoggedIn        User ID that is currently logged in.
     * @return True if reservation was cancelled; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean cancelReservation(String reservationIdToCancel, String userIdLoggedIn) throws SQLException
    {
        PreparedStatement ps1 = connection.prepareStatement("SELECT * " +
                "FROM reservation " +
                "WHERE reservationId = ?" +
                "AND userId = ?");
        ps1.setObject(1, reservationIdToCancel);
        ps1.setObject(2, userIdLoggedIn);
        resultSet = ps1.executeQuery();
        
        if (resultSet.next())
        {
            return deleteReservation(reservationIdToCancel);
        } else
        {
            return false;
        }
    }
    
    /**
     * Calls deleteReservation on all inactive reservations in the database.
     *
     * @return True if one or more expired reservations were deleted; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean deleteAllExpiredReservations() throws SQLException
    {
        updateAllActiveStatus();
        
        Boolean foundExpired = false;
        
        Statement statementSetAllExpired = connection.createStatement();
        ResultSet resultSetAllExpired = statementSetAllExpired.executeQuery("SELECT * " +
                "FROM reservation " +
                "WHERE isActive = FALSE");
        
        while (resultSetAllExpired.next())
        {
            foundExpired = deleteReservation(resultSetAllExpired.getString("reservationId"));
        }
        
        return foundExpired;
    }
    
    /**
     * Compares the current time to all reservation expiration dates in the database.
     * If the current time is after the expiration date, updates the active status of
     * that reservation to inactive.
     *
     * @throws SQLException Executes SQL database query.
     */
    private static void updateAllActiveStatus() throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM reservation");
        while (resultSet.next())
        {
            String cursorReservationId = resultSet.getString("reservationId");
            Timestamp cursorExpDate = resultSet.getTimestamp("reservationExpireDate");
            Timestamp currentDate = Timestamp.from(Instant.now());
            Boolean cursorIsActive = currentDate.before(cursorExpDate);
            String cursorRentableId = resultSet.getString("rentableId");
            
            if (!cursorIsActive)// if not active
            {
                //if room reservation, free up the room
                if (resultSet.getString("reservationType").equals("ROOM"))
                {
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE room " +
                            "SET isReserved = FALSE " +
                            "WHERE roomNumber = ?");
                    ps1.setObject(1, cursorRentableId);
                    ps1.execute();
                }
                //update active status
                PreparedStatement ps2 = connection.prepareStatement("UPDATE reservation " +
                        "SET isActive = ? " +
                        "WHERE reservationId = ?");
                ps2.setObject(1, cursorIsActive);
                ps2.setObject(2, cursorReservationId);
                ps2.execute();
            }
        }
    }
    
    /**
     * Searches for a reservation in the database by reservation ID.
     *
     * @param idToSearch Reservation ID to search for.
     * @return True if the reservation with the given reservation ID is found; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByReservationId(String idToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("reservationId").equals(idToSearch))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Searches for a reservation in the database by rentable ID.
     *
     * @param idToSearch Rentable ID to search for.
     * @return True if any reservation is found with the given rentable ID; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByRentableId(String idToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("rentableId").equals(idToSearch))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Searches for a reservation in the database by user ID.
     *
     * @param idToSearch User ID to search for.
     * @return True if any reservation is found under given user ID; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByUserId(String idToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("userId").equals(idToSearch))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if the reservations table of the database is empty; false if not.
     *
     * @return True if the reservations table of the database is empty; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean isEmpty() throws SQLException
    {
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM  reservation");
        resultSet.next();
        return resultSet.getInt(1) == 0;
    }
}
