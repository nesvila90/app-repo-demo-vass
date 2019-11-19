package com.nexos.person.repository.person;

import com.nexos.person.domain.entity.PersonAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * The interface Person audit repository.
 */
@Repository
public interface PersonAuditRepository extends ReactiveMongoRepository<PersonAudit, BigInteger> {

}
