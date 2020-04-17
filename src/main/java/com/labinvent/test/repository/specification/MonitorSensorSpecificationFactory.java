package com.labinvent.test.repository.specification;

import com.labinvent.test.repository.entity.MonitorSensor;
import com.labinvent.test.repository.entity.Type;
import com.labinvent.test.repository.entity.Unit;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;

public class MonitorSensorSpecificationFactory {
    private MonitorSensorSpecificationFactory() {
    }


    public static Specification<MonitorSensor> getMonitorSensorsByLikeDescription(String description) {
        return getMonitorSensorByLikeStringField(MonitorSensorProperties.DESCRIPTION, description);
    }

    public static Specification<MonitorSensor> getMonitorSensorsByLikeLocation(String location) {
        return getMonitorSensorByLikeStringField(MonitorSensorProperties.LOCATION, location);
    }

    public static Specification<MonitorSensor> getMonitorSensorsByLikeModel(String model) {
        return getMonitorSensorByLikeStringField(MonitorSensorProperties.MODEL, model);
    }

    public static Specification<MonitorSensor> getMonitorSensorsByLikeName(String name) {
        return getMonitorSensorByLikeStringField(MonitorSensorProperties.NAME, name);
    }


    public static Specification<MonitorSensor> getMonitorSensorsByLikeType(String type) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<MonitorSensor, Type> certificateTagListJoin = root.joinList(MonitorSensorProperties.TYPE);
            return criteriaBuilder.like(criteriaBuilder.lower(certificateTagListJoin.get(MonitorSensorProperties.NAME)), getContainsLikePattern(type).toLowerCase());
        };
    }

    public static Specification<MonitorSensor> getMonitorSensorsByLikeUnit(String unit) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<MonitorSensor, Unit> certificateTagListJoin = root.joinList(MonitorSensorProperties.UNIT);
            return criteriaBuilder.like(criteriaBuilder.lower(certificateTagListJoin.get(MonitorSensorProperties.NAME)), getContainsLikePattern(unit).toLowerCase());
        };
    }


    private static Specification<MonitorSensor> getMonitorSensorByLikeStringField(String nameField, String value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(nameField)), getContainsLikePattern(value).toLowerCase());
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        } else {
            return "%" + searchTerm + "%";
        }
    }

}