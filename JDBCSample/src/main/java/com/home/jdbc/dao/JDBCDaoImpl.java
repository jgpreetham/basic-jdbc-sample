package com.home.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.home.jdbc.entity.Person;
import com.home.jdbc.helpers.JDBCHelper;

/**
 * SQL:
 * create table PERSON (ID bigint not null, EMAIL varchar(255), FIRST_NAME varchar(255), JOINED_DATE date, LAST_NAME varchar(255), primary key (id))
 * This class is used to test CRUD operations on DB.
 * 
 * @author preetham
 */
public class JDBCDaoImpl
{
   public static final String INSERT_SQL_QUERY     = "INSERT INTO PERSON(ID,FIRST_NAME,LAST_NAME,EMAIL,JOINED_DATE) VALUES(?,?,?,?,?)";
   public static final String UPDATE_SQL_QUERY     = "UPDATE PERSON SET FIRST_NAME=? WHERE ID=?";
   public static final String SELECT_SQL_QUERY     = "SELECT ID,FIRST_NAME,LAST_NAME,EMAIL,JOINED_DATE FROM PERSON WHERE ID=?";
   public static final String SELECT_ALL_SQL_QUERY = "SELECT ID,FIRST_NAME,LAST_NAME,EMAIL,JOINED_DATE FROM PERSON";
   public static final String DELETE_SQL_QUERY     = "DELETE FROM PERSON WHERE ID=?";
   public static final String DELETE_ALL_SQL_QUERY = "DELETE FROM PERSON";

   public static void main( String[] args )
   {
      Person person = new Person( 1, "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Person person2 = new Person( 2, "Forest", "Gump", "forestgump@jamesbond.com", new java.util.Date() );

      try
      {
         // Create
         insertPerson( person );
         insertPerson( person2 );
         System.out.println( "Persons got inserted sucessfully. This is \"C\" of CRUD " );
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );

         // Read(get all)
         List<Person> persons = retrivePersons();
         System.out.println( "Retrived all persons from DB.This is \"R\" of CRUD " );
         for ( Person p : persons )
         {
            System.out.println( p );
         }
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );
         // Update
         person.setFirstName( "Updated name" );
         updatePersonFirstName( person );
         System.out.println( "Updated the first name of person 2. This is \"U\" of CRUD " );
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );
         // Read(get one )
         Person tempPerson2 = retrivePerson( 2 );
         System.out.println( "Retrived person2 from DB " + tempPerson2 );
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );
         // Delete
         deletePerson( 2 );
         System.out.println( "Deleted person2 from DB.This is \"D\" of CRUD " );
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );
         // Read(get all)
         List<Person> tempPersons = retrivePersons();
         System.out.println( "Retrived all persons from DB. Notice person 2 is not present" );
         for ( Person p : tempPersons )
         {
            System.out.println( p );
         }
         System.out.println();
         System.out.println( "--------------------------------------------------------------------------------------" );
         // Delete
         deleteAllRecords();
         System.out.println( "Deleted all records" );
      }
      catch ( SQLException e )
      {
         System.out.println( "Exception occured " + e.getMessage() );
      }
      catch ( Exception e )
      {
         System.out.println( "Exception occured " + e.getMessage() );
      }
   }

   private static void deleteAllRecords() throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         ps = con.prepareStatement( DELETE_ALL_SQL_QUERY );
         ps.execute();
         System.out.println( "deleteAllRecords => " + ps.toString() );
      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
   }

   private static void deletePerson( int id ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement( DELETE_SQL_QUERY );
         ps.setLong( 1, id );
         ps.execute();
         System.out.println( "deletePerson => " + ps.toString() );
      }
      catch ( SQLException e )
      {
         throw e;
      }

      finally
      {
         try
         {
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
   }

   private static Person retrivePerson( long id ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      Person person = new Person();
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return person;
         }
         ps = con.prepareStatement( SELECT_SQL_QUERY );
         ps.setLong( 1, id );
         rs = ps.executeQuery();
         System.out.println( "retrivePerson => " + ps.toString() );
         while ( rs.next() )
         {
            person.setId( rs.getLong( "ID" ) );
            person.setFirstName( rs.getString( "FIRST_NAME" ) );
            person.setLastName( rs.getString( "LAST_NAME" ) );
            person.setEmail( rs.getString( 4 ) ); // this is to show that we can retrive using index of the column
            person.setJoinedDate( rs.getDate( "JOINED_DATE" ) );
         }

      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closeResultSet( rs );
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
      return person;
   }

   private static void updatePersonFirstName( Person person ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         con.setAutoCommit( false );
         ps = con.prepareStatement( UPDATE_SQL_QUERY );
         ps.setString( 1, person.getFirstName() );
         ps.setLong( 2, person.getId() );
         ps.execute();
         System.out.println( "updatePersonFirstName => " + ps.toString() );
         con.commit();

      }
      catch ( SQLException e )
      {
         try
         {
            if ( con != null )
            {
               con.rollback();
               throw e;
            }
         }
         catch ( SQLException e1 )
         {
            throw e1;
         }
      }
      finally
      {
         try
         {
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }

   }

   private static List<Person> retrivePersons() throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      List<Person> persons = new ArrayList<Person>();
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return persons;
         }
         ps = con.prepareStatement( SELECT_ALL_SQL_QUERY );
         rs = ps.executeQuery();
         System.out.println( "retrivePersons => " + ps.toString() );
         while ( rs.next() )
         {
            Person p = new Person();
            p.setId( rs.getLong( "ID" ) );
            p.setFirstName( rs.getString( "FIRST_NAME" ) );
            p.setLastName( rs.getString( "LAST_NAME" ) );
            p.setEmail( rs.getString( 4 ) ); // this is to show that we can retrive using index of the column
            p.setJoinedDate( rs.getDate( "JOINED_DATE" ) );
            persons.add( p );

         }

      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closeResultSet( rs );
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
      return persons;
   }

   private static void insertPerson( Person p ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         con.setAutoCommit( false );
         ps = con.prepareStatement( INSERT_SQL_QUERY );
         ps.setLong( 1, p.getId() );
         ps.setString( 2, p.getFirstName() );
         ps.setString( 3, p.getLastName() );
         ps.setString( 4, p.getEmail() );
         ps.setDate( 5, new Date( p.getJoinedDate().getTime() ) );

         ps.execute();
         System.out.println( "insertPerson => " + ps.toString() );
         con.commit();

      }
      catch ( SQLException e )
      {
         try
         {
            if ( con != null )
            {
               con.rollback();
            }
         }
         catch ( SQLException e1 )
         {
            throw e1;
         }
         throw e;
      }
      finally
      {
         try
         {
            JDBCHelper.closePrepaerdStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }

   }

}
