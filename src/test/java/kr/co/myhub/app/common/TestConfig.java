package kr.co.myhub.app.common;


import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations={"classpath:/config/spring/context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public abstract class TestConfig {

}
