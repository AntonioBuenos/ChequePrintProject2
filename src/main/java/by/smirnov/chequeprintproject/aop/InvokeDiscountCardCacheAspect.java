package by.smirnov.chequeprintproject.aop;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.repository.DiscountCardCache;
import by.smirnov.chequeprintproject.service.restservice.DiscountCardService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class InvokeDiscountCardCacheAspect {

    private final DiscountCardService service;
    private final DiscountCardCache cache;

    @Pointcut(value = "execution(* by.smirnov.chequeprintproject.service.restservice.DiscountCardService.findById(..)) && args(id))", argNames = "id")
    public void aroundDiscountCardServiceFindByIdPointcut(Long id) {
        //Declares pointcut
    }

    @Around(value = "aroundDiscountCardServiceFindByIdPointcut(id)", argNames = "joinPoint,id")
    public Object useCacheInFindDiscountCardById(ProceedingJoinPoint joinPoint, Long id) throws Throwable {

        DiscountCard card = cache.findById(id);
        if (card != null) return card;

        card = (DiscountCard) joinPoint.proceed();

        return cache.create(card);
    }

    @Pointcut(value = "execution(* by.smirnov.chequeprintproject.service.restservice.DiscountCardService.create(..)) && args(card))", argNames = "card")
    public void afterDiscountCardServiceCreatePointcut(DiscountCard card) {
        //Declares pointcut
    }

    @After(value = "afterDiscountCardServiceCreatePointcut(card)", argNames = "card")
    public void useCacheInCreateDiscountCard(DiscountCard card) {
        cache.create(card);
    }

    @Pointcut(value = "execution(* by.smirnov.chequeprintproject.service.restservice.DiscountCardService.update(..)) && args(card))", argNames = "card")
    public void afterDiscountCardServiceUpdatePointcut(DiscountCard card) {
        //Declares pointcut
    }

    @After(value = "afterDiscountCardServiceUpdatePointcut(card)", argNames = "card")
    public void useCacheInUpdateDiscountCard(DiscountCard card) {
        cache.update(card);
    }

    @Pointcut(value = "execution(* by.smirnov.chequeprintproject.service.restservice.DiscountCardService.delete(..)) && args(id))", argNames = "id")
    public void afterDiscountCardServiceDeletePointcut(Long id) {
        //Declares pointcut
    }

    @After(value = "afterDiscountCardServiceDeletePointcut(id)", argNames = "id")
    public void useCacheInDeleteDiscountCard(Long id) {
        cache.delete(id);
    }
}
