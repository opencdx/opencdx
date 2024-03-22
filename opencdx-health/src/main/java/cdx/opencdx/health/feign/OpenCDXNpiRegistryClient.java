package cdx.opencdx.health.feign;

import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiJsonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface for the NPI Registry client.
 */
@FeignClient(name = "npiRegistryClient")
public interface OpenCDXNpiRegistryClient {

    public static final String NPI_VERSION = "2.1";

    /**
     * Method to get provider info from NPI Registry
     * @param version Version of the API, currently this should always be 2.1
     * @param providerNumber provider number to load info.
     * @return OpenCDXDtoNpiJsonResponse DTO of the JSON response
     */
    @GetMapping
    ResponseEntity<OpenCDXDtoNpiJsonResponse> getProviderInfo(@RequestParam("version") String version, @RequestParam("number") String providerNumber);

    @GetMapping
    ResponseEntity<String> getProviderInfoTest(@RequestParam("version") String version, @RequestParam("number") String providerNumber);
}
