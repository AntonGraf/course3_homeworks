package sky.pro.course3.homeworks.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Value("${server.port}")
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }
}
