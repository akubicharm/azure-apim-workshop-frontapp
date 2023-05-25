package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestHeader

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import org.slf4j.Logger

import com.example.demo.model.Review
import com.example.demo.model.Zaiko

@Controller
class HtmlController(private val restTemplate: RestTemplate, private val logger: Logger) {

  var FUNC_URL = System.getenv("FUNC_URL") ?: "NOT FOUND"
  var APIM_URL = System.getenv("APIM_URL") ?: "NOT FOUND"

  @GetMapping("/")
  fun blog(model: Model): String {
    model["title"] = "Demo"
    model["func_url"] = FUNC_URL
    model["apim_url"] = APIM_URL
    return "demo"
  }

  @GetMapping("/zaiko")
  fun getZaiko(model: Model): String {
        //val l = restTemplate.getForObject("https://apimpub.azure-api.net/zaiko/list", Array<Zaiko>::class.java)
        val l = restTemplate.getForObject(APIM_URL + "/zaiko/list", Array<Zaiko>::class.java)

        logger.info(l?.toList().toString())

        model["title"] = "在庫一覧"
        model.addAttribute("zaiko", l)
        return "zaiko"
  }

  @GetMapping("/review")
  fun getReview(model: Model, @RequestHeader("x-ms-token-aad-access-token") token: String): String{
    logger.info(token)

    //val l = restTemplate.getForObject("https://apimpub.azure-api.net/review/listReviews", Array<Review>::class.java)
    
    val headers: HttpHeaders = HttpHeaders()
    val bt: String = StringBuilder("Bearer  " + token).toString()
    headers.set("Authorization", bt)

    val entity: HttpEntity<Any> = HttpEntity(headers)

    logger.info(entity.toString())
    //val res: ResponseEntity<String> = restTemplate.exchange("https://apimpub.azure-api.net/review/listReviews", HttpMethod.GET, entity, String::class.java)
    val res: ResponseEntity<String> = restTemplate.exchange(APIM_URL + "/review/listReviews", HttpMethod.GET, entity, String::class.java)

    logger.info(res.toString())
    logger.info(res.getBody())

    val json = res.getBody()
    val reviews = jacksonObjectMapper().readValue(json, Array<Review>::class.java)
    
    model["title"] = "商品レビュー"
    model.addAttribute("review", reviews)
    return "review"    
  }

 @GetMapping("/func")
  fun getFunc(model: Model, @RequestHeader("x-ms-token-aad-access-token") token: String): String{
    logger.info(token)

    val headers: HttpHeaders = HttpHeaders()
    val bt: String = StringBuilder("Bearer  " + token).toString()
    headers.set("Authorization", bt)
    val entity: HttpEntity<Any> = HttpEntity(headers)

    logger.info(entity.toString())
    //val res: ResponseEntity<String> = restTemplate.exchange("https://apimwsakubicharm.azurewebsites.net/api/listReviews", HttpMethod.GET, entity, String::class.java)
    val res: ResponseEntity<String> = restTemplate.exchange(FUNC_URL + "/api/listReviews", HttpMethod.GET, entity, String::class.java)


    logger.info(res.toString())
    logger.info(res.getBody())

    val json = res.getBody()
    val reviews = jacksonObjectMapper().readValue(json, Array<Review>::class.java)
    
    model["title"] = "商品レビュー"
    model.addAttribute("review", reviews)
    return "review"    
  }  
}