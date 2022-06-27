package com.example.rest.domain.imdb.scheduler;

import com.example.rest.domain.imdb.service.impl.IMDbAsyncServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RankingScheduler {

    private final IMDbAsyncServiceImpl imDbAsyncService;

    @Async
    @Scheduled(cron = "0 34 10 * * ?")
    public void getTop250Movies() throws IOException {
        imDbAsyncService.getTop250Movies();
        log.info("Scheduled top 250 movies update");
    }

    @Async
    @Scheduled(cron = "0 0 12 * * ?")
    public void getTop250TVs() throws IOException{
        imDbAsyncService.getTop250Movies();
        log.info("Scheduled top 250 tvs update");
    }
}
