package com.stackroute.analyticsservice.service;

import com.stackroute.analyticsservice.cassandra.QueryResultResponse;
import com.stackroute.analyticsservice.cassandra.QueryResultResponseKey;
import com.stackroute.analyticsservice.cassandra.QueryResultResponseRepository;
import com.stackroute.analyticsservice.domain.AnalyticsOutput;
import com.stackroute.analyticsservice.domain.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{

    private QueryResultResponseRepository queryResultResponseRepository;
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

    public AnalyticsServiceImpl(QueryResultResponseRepository queryResultResponseRepository) {
        this.queryResultResponseRepository = queryResultResponseRepository;
    }

    @Override
    public boolean existsByQuery(String query) {
        return queryResultResponseRepository.existsByKeyQuery(query);
    }

    @Override
    public void saveQuery(Input input) {
        QueryResultResponseKey key = new QueryResultResponseKey();
        key.setDomain(input.getDomain());
        key.setQuery(input.getQuery());
        logger.debug(input.getResponse());
        if(input.getResponse().equals("accurate"))
        {
            key.setPosResponse(1);
        }
        else if(input.getResponse().equals("inaccurate"))
        {
            key.setNegResponse(1);
        }
        QueryResultResponse resultResponse = new QueryResultResponse();
        resultResponse.setKey(key);
        resultResponse.setResult(input.getResult());
        logger.debug(resultResponse.toString());
        queryResultResponseRepository.insert(resultResponse);
    }

    @Override
    public void updateQuery(Input input) {
        QueryResultResponse resultResponse = queryResultResponseRepository.findByKeyQuery(input.getQuery());
        queryResultResponseRepository.delete(resultResponse);
        QueryResultResponseKey key = new QueryResultResponseKey();
        key.setQuery(input.getQuery());
        key.setDomain(input.getDomain());
        key.setPosResponse(resultResponse.getKey().getPosResponse());
        key.setNegResponse(resultResponse.getKey().getNegResponse());
        logger.debug(input.getResponse());
        if(input.getResponse().equals("accurate"))
        {
            key.setPosResponse(key.getPosResponse()+1);
        }
        else if(input.getResponse().equals("inaccurate"))
        {
            key.setNegResponse(key.getNegResponse()+1);
        }
        resultResponse.setKey(key);
        queryResultResponseRepository.insert(resultResponse);
    }

    @Override
    public List<QueryResultResponse> getResults() {
        return queryResultResponseRepository.findAll();
    }

    @Override
    public List<QueryResultResponse> getResultsByDomain(String domain) {
        return queryResultResponseRepository.findByKeyDomain(domain);
    }

    @Override
    public AnalyticsOutput getAnalyticsData() {
        AnalyticsOutput output = new AnalyticsOutput();
        List<QueryResultResponse> mov = getResultsByDomain("movie");
        List<QueryResultResponse> med = getResultsByDomain("medical");
        int movpr=0;
        int movnr=0;
        int medpr=0;
        int mednr=0;
        for(QueryResultResponse a: mov)
        {
            movpr = movpr + a.getKey().getPosResponse();
            movnr = movnr + a.getKey().getNegResponse();
        }
        for(QueryResultResponse b: med)
        {
            medpr = medpr + b.getKey().getPosResponse();
            mednr = mednr + b.getKey().getNegResponse();
        }
        output.setMovPosResp(movpr);
        output.setMovNegResp(movnr);
        output.setMedPosResp(medpr);
        output.setMedNegResp(mednr);
        output.setMovAcc(((double)movpr/(movnr+movpr))*100.0);
        output.setMedAcc(((double)medpr/(mednr+medpr))*100.0);
        return output;
    }
}
