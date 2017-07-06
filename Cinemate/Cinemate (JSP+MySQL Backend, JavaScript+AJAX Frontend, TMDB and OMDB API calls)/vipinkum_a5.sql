DROP DATABASE if exists vipinkum_cinemate;

CREATE DATABASE vipinkum_cinemate;

USE vipinkum_cinemate;

CREATE TABLE Users (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null,
  fname varchar(50) not null,
  lname varchar(50) not null,
  imgurl varchar(50) not null
);

CREATE TABLE Follows (
-- userfID follows followingID
  fID int(11) primary key not null auto_increment,
  userfID int(11) not null , 
  followingID int(11) not null ,
  FOREIGN KEY (userfID) REFERENCES Users(userID),
  FOREIGN KEY (followingID) REFERENCES Users(userID)
);

CREATE TABLE Events (
  eventID int(11) primary key not null auto_increment,
  userID int(11) not null , 
  movie varchar(50) not null,
  rating double not null,
  action varchar(50) not null,
  EventDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (userID) REFERENCES Users(userID)
);

CREATE TABLE Movies (
	movieID int(11) primary key not null auto_increment,
    title varchar(50) not null,
	totalrating int(11) not null,
    totalvotes int(11) not null    
);
