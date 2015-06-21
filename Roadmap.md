# Introduction #

This application is written for Android.
It should help to organize a meeting and exchange documents beforehand.


## version 0.0.1 ##
Allows to define a date, start and end time and view people with id in (1,2,3). Makes use of the People Content Provider, date picker dialog and time picker dialog
### Activities ###
  * AMeeting (MAIN)
### Lessons learned ###
  * content provider: query always with projection that contains id.
  * date picker dialog: must be initialized with values other than 0, 0, 0