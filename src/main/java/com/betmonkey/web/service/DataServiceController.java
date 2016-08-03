package com.betmonkey.web.service;


import com.betmonkey.DataProvider;
import com.betmonkey.domain.*;
import com.betmonkey.enums.*;
import com.betmonkey.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by scott on 03/08/16.
 */
@RestController
public class DataServiceController {


    DataProvider provider = null;

    @Autowired
    public DataServiceController(DataProvider provider)
    {
        this.provider = provider;

    }

    @RequestMapping("/event")
    public List<EventTypeResult> getEventTypeResult() throws DataRetrievalException
    {
        List<EventTypeResult> results = null;

            MarketFilter marketFilter;
            marketFilter = new MarketFilter();
            results = provider.listEventTypes(marketFilter);
        return results;
    }

    @RequestMapping("/event/{id}/market")
    public List<MarketCatalogue> getEventMarkets(@PathVariable("id")Set<String> eventTypeIds,
                                                 @RequestParam(value = "country", required = false) String country,
                                                 @RequestParam(value = "type", required = false) String type,
                                                 @RequestParam (value = "pastDate", required=false) Boolean pastDate) throws DataRetrievalException
    {
        TimeRange time = new TimeRange();
        if(pastDate!=null)
        {
            if(!pastDate)
                time.setFrom(new Date());
        }

        Set<String> countries = new HashSet<String>();
        if(country!=null) countries.add(country);

        Set<String> typeCode = new HashSet<String>();
        if(type!=null) typeCode.add(type);

        MarketFilter marketFilter = new MarketFilter();
        marketFilter.setEventTypeIds(eventTypeIds);
        marketFilter.setMarketStartTime(time);
        marketFilter.setMarketCountries(countries);
        marketFilter.setMarketTypeCodes(typeCode);

        Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
       // marketProjection.add(MarketProjection.RUNNER_METADATA);
        marketProjection.add(MarketProjection.MARKET_START_TIME);
        marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);
        marketProjection.add(MarketProjection.MARKET_DESCRIPTION);

        //marketProjection.addAll(new HashSet<MarketProjection>(Arrays.asList(MarketProjection.values())));



        List<MarketCatalogue> marketCatalogueResult = provider.listMarketCatalogue(marketFilter,marketProjection, MarketSort.FIRST_TO_START,"1000");
        return marketCatalogueResult;
    }

    @RequestMapping("/marketbook/{id}")
    public List<MarketBook> getMarketBook(@PathVariable("id") String marketId) throws DataRetrievalException
    {
        PriceProjection priceProjection = new PriceProjection();
        Set<PriceData> priceData = new HashSet<PriceData>();
        priceData.add(PriceData.EX_BEST_OFFERS);
        priceProjection.setPriceData(priceData);

        //In this case we don't need these objects so they are declared null
        OrderProjection orderProjection = null;
        MatchProjection matchProjection = null;
        String currencyCode = null;

        List<String> marketIds = new ArrayList<String>();
        marketIds.add(marketId);
        List<MarketBook> marketBookReturn = provider.listMarketBook(marketIds, priceProjection,
                orderProjection, matchProjection, currencyCode);

        return marketBookReturn;

    }


    @ExceptionHandler({DataRetrievalException.class})
    public ResponseEntity retrievalError(DataRetrievalException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST : " + e.toString());
    }

}
