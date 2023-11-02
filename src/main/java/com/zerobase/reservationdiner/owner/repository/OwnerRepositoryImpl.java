package com.zerobase.reservationdiner.owner.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.zerobase.reservationdiner.owner.domain.QOwnerStore.ownerStore;

@RequiredArgsConstructor
public class OwnerRepositoryImpl implements CustomOwnerRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<StoreInfo> findBydistanceOrderByStoreNameAndStar(double lat, double lnt) {

        NumberExpression<Double> acosExpression = getDistance(lat, lnt);

        return queryFactory.select(
                        Projections.constructor(StoreInfo.class, ownerStore.storeName,
                                Projections.bean(AddressDto.class, ownerStore.address.street, ownerStore.address.city),
                                ownerStore.id, ownerStore.star))
                .from(ownerStore)
                .orderBy(acosExpression.asc(), ownerStore.storeName.asc(), ownerStore.star.desc())
                .fetch();
    }

    @Override
    public List<StoreInfo> findBydistanceAndStoreNameOrderByStoreNameAndStar(double lat, double lnt, String storeName) {
        NumberExpression<Double> acosExpression = getDistance(lat, lnt);

        return queryFactory.select(
                        Projections.constructor(StoreInfo.class, ownerStore.storeName,
                                Projections.bean(AddressDto.class, ownerStore.address.street, ownerStore.address.city),
                                ownerStore.id, ownerStore.star))
                .from(ownerStore)
                .where(ownerStore.storeName.like("%"+storeName+"%"))
                .orderBy(acosExpression.asc(), ownerStore.storeName.asc(), ownerStore.star.desc())
                .fetch();
    }
    private static NumberExpression<Double> getDistance(double lat, double lnt) {
        NumberExpression<Double> acosExpression = Expressions.numberTemplate(Double.class,
                "6371 * acos( cos( radians({0}) ) * cos( radians(ownerStore.address.lat) ) * cos( radians(ownerStore.address.lnt) - radians({1}) ) + sin( radians({0}) ) * sin( radians(ownerStore.address.lat) ) )",
                lat, lnt);
        return acosExpression;
    }

}
