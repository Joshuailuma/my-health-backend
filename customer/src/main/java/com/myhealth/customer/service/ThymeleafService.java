package com.myhealth.customer.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public  interface ThymeleafService {
     String createContent (String template, Map< String, Object > variables);
}
