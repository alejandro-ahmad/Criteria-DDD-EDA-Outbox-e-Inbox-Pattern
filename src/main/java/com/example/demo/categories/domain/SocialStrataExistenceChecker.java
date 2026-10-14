package com.example.demo.categories.domain;

import com.example.demo.categories.domain.valobj.SocialStrataId;

public interface SocialStrataExistenceChecker {
    boolean exists(SocialStrataId id);
}
