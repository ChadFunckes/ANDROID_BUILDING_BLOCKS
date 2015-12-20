# ANDROID_BUILDING_BLOCKS
Test files for basic android operations, for code snippet uses

alarm sample ... is a basic setting an alarm with a reciever 
------------
date_time_picker_alarm has a both date and time picker fragements shown as dialogs from the main
activity.  The set alarm button set an alarm event with the reciever in AlarmReciever, which sets up
and displays a notification upon alarm reciept.  The manifest is also set up for a verified working
on-boot reciever (BootCompletedReciever) to set alarms back up when system reboots....however this program
does not store alarms, so this reciever is blank in this file.
--------------
expandable list test is just a basic expandable list example, using two custom containers I was using
to make another projects... it's ugly but works, just for expandable list adapter reference
----------
JSONExample is how to pull JSON data ** the json data might not still exist at the example site... 
the format for JSON data was:

{"catlog":[{"ITEM_ID":"1","CAT_ID":"1","ITEM_NAME":"Red Bike","ITEM_DESC":"The reddest bike in town!!","ITEM_IMAGE":"Images\/Red-Bike.jpg","ITEM_THUMB":""},{"ITEM_ID":"2","CAT_ID":"1","ITEM_NAME":"Blue bike","ITEM_DESC":"the bluest bike evar!!!","ITEM_IMAGE":"Images\/blue-Bike.jpg","ITEM_THUMB":""},{"ITEM_ID":"3","CAT_ID":"1","ITEM_NAME":"yellow bike","ITEM_DESC":"this bike is sooooooo yellow!!","ITEM_IMAGE":"Images\/yellow-Bike.jpg","ITEM_THUMB":""},{"ITEM_ID":"4","CAT_ID":"1","ITEM_NAME":"white bike","ITEM_DESC":"in spain this bike is called the bicicleta blanco","ITEM_IMAGE":"Images\/white-Bike.jpg","ITEM_THUMB":""},{"ITEM_ID":"5","CAT_ID":"2","ITEM_NAME":"red shirt","ITEM_DESC":"get a red shirt to match your red bike son!!","ITEM_IMAGE":"Images\/red-shirt.jpg","ITEM_THUMB":""},{"ITEM_ID":"6","CAT_ID":"2","ITEM_NAME":"blue shirt","ITEM_DESC":"this shirt will go great even without the blue bike!","ITEM_IMAGE":"Images\/blue-shirt.jpg","ITEM_THUMB":""},{"ITEM_ID":"7","CAT_ID":"2","ITEM_NAME":"yellow shirt","ITEM_DESC":"this shirt will blind people its so yellow.","ITEM_IMAGE":"Images\/yellow-shirt.jpg","ITEM_THUMB":""},{"ITEM_ID":"8","CAT_ID":"2","ITEM_NAME":"white shirt","ITEM_DESC":"this is the whitest shirt I have ever seeeeeeen!!! Don't spill any spaghetti!","ITEM_IMAGE":"Images\/white-shirt.jpg","ITEM_THUMB":""},{"ITEM_ID":"9","CAT_ID":"3","ITEM_NAME":"front derailleur","ITEM_DESC":"get those big gears in order with out super front derailleur!!","ITEM_IMAGE":"Images\/front-d.jpg","ITEM_THUMB":""},{"ITEM_ID":"10","CAT_ID":"3","ITEM_NAME":"rear derailleur","ITEM_DESC":"make sure you can get up those hills!!","ITEM_IMAGE":"Images\/rear-d.jpg","ITEM_THUMB":""},{"ITEM_ID":"11","CAT_ID":"3","ITEM_NAME":"front brakes","ITEM_DESC":"Don't fall over your handlebars!!","ITEM_IMAGE":"Images\/front-b.jpg","ITEM_THUMB":""},{"ITEM_ID":"12","CAT_ID":"3","ITEM_NAME":"rear brakes","ITEM_DESC":"These are the most stoppinest brakes ever!","ITEM_IMAGE":"Images\/rear-b.jpg","ITEM_THUMB":""}]}

--------------

outdoorandroid was done for a class...pulls json from the internet, stores it in sql...rechecks josn every hour...makes a list of the data..
onclick loads a details page.  the app allows for favorite item storage *** data source may not still be available ***