package com.bytebybyte.mapquest.geocoding.service.standard;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bytebybyte.mapquest.geocoding.service.IGeocodeRequest;
import com.bytebybyte.mapquest.geocoding.service.IGeocodingService;
import com.bytebybyte.mapquest.geocoding.service.IResponse;
import com.bytebybyte.mapquest.geocoding.service.IReverseGeocodeRequest;
import com.bytebybyte.mapquest.geocoding.service.response.Response;

public class StandardGeocodingService implements IGeocodingService {

	protected static final Logger logger = LoggerFactory.getLogger(StandardGeocodingService.class);

	protected static final String ADDRESS_URL = "http://open.mapquestapi.com/geocoding/v1/address";
	protected static final String REVERSE_URL = "http://open.mapquestapi.com/geocoding/v1/reverse";

	protected RestTemplate restTemplate;

	public StandardGeocodingService() {
		this(new RestTemplate());
	}

	public StandardGeocodingService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public IResponse geocode(IGeocodeRequest request) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ADDRESS_URL);

		for (Map.Entry<String, String> entry : request.getParameters().entrySet())
			builder.queryParam(entry.getKey(), entry.getValue());

		URI uri = builder.build().toUri();

		return restTemplate.getForObject(uri, Response.class);
	}

	@Override
	public IResponse reverseGeocode(IReverseGeocodeRequest request) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REVERSE_URL);

		for (Map.Entry<String, String> entry : request.getParameters().entrySet())
			builder.queryParam(entry.getKey(), entry.getValue());

		URI uri = builder.build().toUri();

		return restTemplate.getForObject(uri, Response.class);
	}
}
