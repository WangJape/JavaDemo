package httpdemoclient.service;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {

    @Autowired
    private HttpClient httpClient;



}
