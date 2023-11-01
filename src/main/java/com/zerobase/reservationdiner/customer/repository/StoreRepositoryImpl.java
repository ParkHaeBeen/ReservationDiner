package com.zerobase.reservationdiner.customer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.customer.dto.StoreInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.zerobase.reservationdiner.owner.domain.QOwnerStore.*;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements CustomStoreRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<StoreInfo> findBydistanceOrderByStoreNameAndStart(double lat, double lnt) {

        NumberExpression<Double> acosExpression = Expressions.numberTemplate(Double.class,
                "6371 * acos( cos( radians({0}) ) * cos( radians(ownerStore.address.lat) ) * cos( radians(ownerStore.address.lnt) - radians({1}) ) + sin( radians({0}) ) * sin( radians(ownerStore.address.lat) ) )",
                lat, lnt);

        List<StoreInfo> stores = queryFactory.select(
                        Projections.constructor(StoreInfo.class, ownerStore.storeName, ownerStore.address, ownerStore.id, ownerStore.star))
                .from(ownerStore)
                .orderBy(acosExpression.asc(), ownerStore.storeName.asc(), ownerStore.star.asc())
                .fetch();

        return stores;
    }

}

