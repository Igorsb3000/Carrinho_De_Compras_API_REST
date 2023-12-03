package br.com.atlantic.api.domain.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({TestesCaixaPreta.class, TestesCaixaBranca.class})
public class SuiteTestes {
}
