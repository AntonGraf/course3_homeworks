package sky.pro.course3.homeworks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);
    @Value("${server.port}")
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }

    public int getSum() {
        logger.info("Запускается метод getSum");
        long startTime = System.nanoTime();

        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .unordered()
                .parallel()
                .reduce(0, Integer::sum);

        logger.debug("Метод выполнился за " + (System.nanoTime() - startTime) / 1_000_000 + " мс");

        return sum;
    }
}
