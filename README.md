# market-price-handler

### High level design decisions

- PriceFeedListener: component responsible for listening to an inbound feed of prices, and submitting them to the storage (In this case, an in memory database).
- MarginService: component responsible for calculating prices with their added margin, given a Bid/Ask margin% and the original value.
- PriceFeedParserService: component responsible for parsing the inbound CSV feed into Price Objects.
- PriceCheckController: The top level REST controller which would serve a GET request on the /fxPrices/ collection, for the given {instrumentName}
- PriceDAO: An interface representing the storage, some sort of database. For this example, I have implemented a simple In memory database backed by a HashMap.
- I am using 0 frameworks as suggested other than jUnit/Mockito, so just plain Java, but in a real application such as this I would have expected to see the likes of Spring thrown in.

### Assumptions
1) As I am not using frameworks and it was requested not to implement the full REST service, the REST Controller is very bare bones. I have left some commented out code with the typical annotations we might use when using Spring to map Controller methods to actual endpoint URIs.
2) I have assumed that bid/ask prices can have up to 4 decimal places and rounded them up where needed when calculating margins, but obviously this could be adjusted.
3) I have assumed that the UI might wish to display the original bid/ask Price, together with the new +margin Price. For this reason I added 4 separate fields to the Price Object: bid, ask, bidWithMargin, askWithMargin
4) I have assumed that Date parsing (Timezone conversion etc) is not required and as such storing the timestamp as a plain String, otherwise we could have parsed this into Objects, apply user timezones, etc.
5) I have assumed that detailed error handling is not required so for the most part I have just logged Exception stack traces where applicable, and thrown 404's where user requests non existing instrument.