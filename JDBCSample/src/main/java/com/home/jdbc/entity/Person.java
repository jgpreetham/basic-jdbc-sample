package com.home.jdbc.entity;

import java.util.Date;

/**
 * @author preetham
 */
public class Person
{
   private long   id;
   private String firstName;
   private String lastName;
   private String email;
   private Date   joinedDate;

   public Person()
   {

   }

   public Person( long id, String firstName, String lastName, String email, Date joinedDate )
   {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.joinedDate = joinedDate;

   }

   public long getId()
   {
      return id;
   }

   public void setId( long id )
   {
      this.id = id;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   public Date getJoinedDate()
   {
      return joinedDate;
   }

   public void setJoinedDate( Date joinedDate )
   {
      this.joinedDate = joinedDate;
   }

   @Override
   public String toString()
   {
      return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", joinedDate=" + joinedDate + "]";
   }

}
