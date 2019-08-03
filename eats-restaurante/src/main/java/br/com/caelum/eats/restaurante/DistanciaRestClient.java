package br.com.caelum.eats.restaurante;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DistanciaRestClient {

	private String distanciaServiceUrl;
	private RestTemplate restTemplate;

	public DistanciaRestClient(RestTemplate restTemplate,
			@Value("${configuracao.distancia.service.url}") String distanciaServiceUrl) {
		this.distanciaServiceUrl = distanciaServiceUrl;
		this.restTemplate = restTemplate;
	}

	public void novoRestauranteAprovado(Restaurante restaurante) {
		RestauranteParaServicoDeDistancia restaurandeDistancia = getRestauranteParaServicoDistancia(restaurante);
		String url = distanciaServiceUrl + "/restaurantes";

		ResponseEntity<RestauranteParaServicoDeDistancia> postForEntity = restTemplate.postForEntity(url,
				restaurandeDistancia, RestauranteParaServicoDeDistancia.class);

		HttpStatus statusCode = postForEntity.getStatusCode();
		if (!HttpStatus.CREATED.equals(statusCode)) {
			throw new RuntimeException("Status diferente do esperado" + statusCode);
		}

	}

	public void restauranteAtualizado(Restaurante restaurante) {
		RestauranteParaServicoDeDistancia restauranteParaServicoDistancia = getRestauranteParaServicoDistancia(
				restaurante);
		String url = distanciaServiceUrl + "/restaurantes" + restaurante.getId();
		restTemplate.put(url, restauranteParaServicoDistancia, RestauranteParaServicoDeDistancia.class);
	}

	private RestauranteParaServicoDeDistancia getRestauranteParaServicoDistancia(Restaurante restaurante) {
		return new RestauranteParaServicoDeDistancia(restaurante);
	}

}
