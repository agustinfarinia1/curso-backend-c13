package com.farinia.desafioTest;

import com.farinia.desafioTest.model.Productos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.client.HttpClientBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DesafioTestApplicationTests {
	static Logger logger = LogManager.getLogger(DesafioTestApplicationTests.class);
	private String url;
	private static long tiempoInicio = System.nanoTime();;

	@LocalServerPort
	private int port;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;


	@BeforeAll
	public static void antesTestGeneral(){
		logger.info("Inicio de los test");
	}
	@BeforeEach
	void init() {
		url = String.format("http://localhost:%d/api/", port);
	}
	@BeforeEach
	public void antesTest(){
		logger.info("Inicio del test");
	}
	@AfterAll
	public static void despuesTestGeneral(){
		logger.info("Final del test");
		logger.info("Finalizó la ejecución de los tests. La misma duró {} ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - tiempoInicio));
	}

	@Test
	public void getAllProducts() throws Exception {
		var uriTest = String.format("%s%s", url, "productos");

		var productResult = this.restTemplate.getForObject(uriTest, List.class);

		Assert.notNull(productResult, "Lista de productos no nula");
		Assert.notEmpty(productResult, "Lista de productos con elementos");

	}

	@Test
	public void createProduct() {
		var uriTest = String.format("%s%s", url, "productos");
		Productos producto = this.restTemplate.postForObject(uriTest, (new Productos(5L, "Pizza", 22,22)) , Productos.class);
		Assert.notNull(producto, "Producto no nulo");
	}

	@Test
	public void updateProduct() {
		var uriTest = String.format("%s%s", url, "productos");
		this.restTemplate.put(uriTest, (new Productos(1L, "Bife", 22,22)) , Productos.class);
	}
	@Test
	public void deleteProduct() {
		var uriTest = String.format("%s%s", url, "productos/1");
		this.restTemplate.delete(uriTest);
	}

	@Test
	public void getAllProductsHttpRequest() throws IOException {
		var uriTest = String.format("%s%s", url, "productos");

		var request = new HttpGet(uriTest);
		var httpResponse =
				HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(
				httpResponse
						.getStatusLine()
						.getStatusCode() == HttpStatus.SC_OK,
				"Response status OK");
	}

	/*
	@Test
	public void createProductHttpRequest() throws IOException {
		var uriTest = String.format("%s%s", url, "productos");

		var request = new HttpPost(uriTest);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", "5L"));
		params.add(new BasicNameValuePair("nombre", "Pizza"));
		params.add(new BasicNameValuePair("precio", "22"));
		params.add(new BasicNameValuePair("stock", "22"));
		request.setEntity(new UrlEncodedFormEntity(params));
		var httpResponse =
				HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(
				httpResponse
						.getStatusLine()
						.getStatusCode() == HttpStatus.SC_OK,
				"Response status OK");
	}

	@Test
	public void updateProductHttpRequest() throws IOException {
		var uriTest = String.format("%s%s", url, "productos");

		var request = new HttpPut(uriTest);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", "1L"));
		params.add(new BasicNameValuePair("nombre", "Pizza"));
		params.add(new BasicNameValuePair("precio", "22"));
		params.add(new BasicNameValuePair("stock", "22"));
		request.setEntity(new UrlEncodedFormEntity(params));
		var httpResponse =
				HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(
				httpResponse
						.getStatusLine()
						.getStatusCode() == HttpStatus.SC_OK,
				"Response status OK");
	}
	 */
	@Test
	public void deleteProductHttpRequest() throws IOException {
		var uriTest = String.format("%s%s", url, "productos/1");

		var request = new HttpDelete(uriTest);
		var httpResponse =
				HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(
				httpResponse
						.getStatusLine()
						.getStatusCode() == HttpStatus.SC_OK,
				"Response status OK");
	}
}
