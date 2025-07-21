package com.outsera.worstmovie;

import com.outsera.worstmovie.dto.AwardIntervalResponseDTO;
import com.outsera.worstmovie.dto.ProducerAwardIntervalDTO;
import com.outsera.worstmovie.factory.ProducerAwardIntervalFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Method: AwardController.getProducerAwardIntervals()")
class GoldenRaspberryAwardsAPITests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ProducerAwardIntervalDTO minProducerDTO = ProducerAwardIntervalFactory.createMinProducer();
    private final ProducerAwardIntervalDTO maxProducerDTO = ProducerAwardIntervalFactory.createMaxProducer();

    @Test
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void shouldReturnProducerAwardIntervals() {
        String url = "http://localhost:" + port + "/awards/producer-intervals";
        ResponseEntity<AwardIntervalResponseDTO> response = restTemplate.getForEntity(url, AwardIntervalResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        AwardIntervalResponseDTO body = response.getBody();
        ProducerAwardIntervalDTO minProducer = body.getMin().getFirst();
        ProducerAwardIntervalDTO maxProducer = body.getMax().getFirst();

        assertThat(body.getMin()).isNotEmpty();
        assertThat(body.getMax()).isNotEmpty();
        assertThat(minProducer.getProducer()).isEqualTo(minProducerDTO.getProducer());
        assertThat(minProducer.getInterval()).isEqualTo(minProducerDTO.getInterval());
        assertThat(minProducer.getPreviousWin()).isEqualTo(minProducerDTO.getPreviousWin());
        assertThat(minProducer.getFollowingWin()).isEqualTo(minProducerDTO.getFollowingWin());
        assertThat(maxProducer.getProducer()).isEqualTo(maxProducerDTO.getProducer());
        assertThat(maxProducer.getInterval()).isEqualTo(maxProducerDTO.getInterval());
        assertThat(maxProducer.getPreviousWin()).isEqualTo(maxProducerDTO.getPreviousWin());
        assertThat(maxProducer.getFollowingWin()).isEqualTo(maxProducerDTO.getFollowingWin());
    }
}
