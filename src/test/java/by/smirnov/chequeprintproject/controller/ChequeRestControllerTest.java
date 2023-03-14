package by.smirnov.chequeprintproject.controller;

import by.smirnov.chequeprintproject.domain.ChequeRequest;
import by.smirnov.chequeprintproject.domain.ChequeResponse;
import by.smirnov.chequeprintproject.service.restservice.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChequeRestControllerTest {

    @Captor
    ArgumentCaptor<ChequeRequest> requestCaptor;

    @Mock
    private ProductService service;

    @InjectMocks
    private ChequeRestController controller;

    private ChequeRequest request;

    private ChequeResponse response;

    private BindingResult bindingResult;

    @BeforeEach
    void init(){
        request = mock(ChequeRequest.class);
        response = mock(ChequeResponse.class);
        bindingResult = mock(BindingResult.class);
    }

    @Test
    @DisplayName("BuildCheque should return equal ResponseEntity")
    void checkShowShouldReturnEqualResponseEntity() {
        ResponseEntity<ChequeResponse> expected = new ResponseEntity<>(response, HttpStatus.OK);
        doReturn(response).when(service).getCheque(request);

        ResponseEntity<ChequeResponse> actual = controller.create(request, bindingResult);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("BuildCheque should pass parameter")
    void checkShowShouldPassParameter() {
        controller.create(request, bindingResult);

        verify(service).getCheque(requestCaptor.capture());
        ChequeRequest value = requestCaptor.getValue();
        assertThat(value).isEqualTo(request);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("BuildCheque should swallow null")
    void checkShowShouldSwallowNull(ChequeRequest request) {
        ResponseEntity<ChequeResponse> expected = new ResponseEntity<>(response, HttpStatus.OK);
        doReturn(response).when(service).getCheque(request);

        ResponseEntity<ChequeResponse> actual = controller.create(request, bindingResult);

        assertThat(actual).isEqualTo(expected);
    }
}
