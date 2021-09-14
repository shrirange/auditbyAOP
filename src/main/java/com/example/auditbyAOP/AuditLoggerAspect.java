package com.example.auditbyAOP;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Aspect
@Configuration
public class AuditLoggerAspect {

	private Logger logger = LoggerFactory.getLogger(AuditLoggerAspect.class);

	@Around(value = "execution(public * save(..)) && this(org.springframework.data.repository.CrudRepository)")
	public Object onSaveExecuted(ProceedingJoinPoint pjp) throws Throwable {
		return fireAudit(pjp, false);
	}

	private Object fireAudit(ProceedingJoinPoint pjp, boolean b) {
		Optional<Class> annotatedInterface = getAnnotatedInterface(pjp);
		if (annotatedInterface.isPresent()) {
			try {
				Object modifiedObject = pjp.proceed(pjp.getArgs());
				System.out.println(modifiedObject);
				return modifiedObject;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Optional<Class> getAnnotatedInterface(JoinPoint pjp) {
		for (Class i : pjp.getTarget().getClass().getInterfaces()) {
			if (i.isAnnotationPresent(AUDITABLE_REPO.class) && CrudRepository.class.isAssignableFrom(i)) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}

}
