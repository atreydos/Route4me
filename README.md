# Route4me



## Technical task

**1.** Uses exchange rate data from this web service: https://exchangeratesapi.io

**2.** Shows the latest exchange rates in a listview. USD should be used as base currency. Each list item of the listview should have 2 rows: the currency name in the first row and
the latest exchange rate (with two decimal precision) in the second row:

| Ex.                  |
| -------------------- |
|    DKK<br/>6.74      |
|    HUF<br/>299.56    |
|    ......            |
      
      
Once the currency data is loaded from the web service, save it in a local database too. Also, save the timestamp of the last request somewhere.

Next time user opens the app you should check whether 10 minutes elapsed since the last request:
  <br/>**a.** If yes, you should load new data from web service.
  <br/>**b.** If no, you should load previously saved data from the local database.

**3.** If user clicks on a list item a new screen should be opened which shows the exchange rate graph/chart of the selected currency for the last 7 days. Here it is not necessary to save anything in the local database, you should request every time the currency data for the last 7 days.

`*You can use any third party library for drawing currency graph/chart. If web service doesn’t return data for the last 7 days, please show a warning popup with the following message: “No exchange rate data is available for the selected currency.”`
