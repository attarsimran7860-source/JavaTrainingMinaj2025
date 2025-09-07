package com.day5.lab1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DepartmentReportCollector {

	public static Collector<Employee, Map<String, DepartmentReportAccumulator>, Map<String, Department>>
    departmentReportCollector() {

        // Supplier: Provides a new mutable result container (Map<String, DepartmentReportAccumulator>)
        // for collecting employees. Each key is a department name, and its value is an
        // accumulator for that department.
        Supplier<Map<String, DepartmentReportAccumulator>> supplier = HashMap::new;

        // Accumulator: Incorporates a single Employee into the mutable result container.
        // It gets the department of the employee and either creates a new accumulator
        // for that department or updates an existing one.
        BiConsumer<Map<String, DepartmentReportAccumulator>, Employee> accumulator = (accMap, employee) -> {
            String departmentName = employee.getDepartment();
            // If the department is not yet in the map, create a new accumulator for it.
            // Then, accumulate the current employee into that accumulator.
            accMap.computeIfAbsent(departmentName, DepartmentReportAccumulator::new)
                  .accumulate(employee);
        };

        // Combiner: Merges two mutable result containers (maps of accumulators).
        // This is crucial for parallel streams. It iterates over one map and merges its
        // department-specific accumulators into the other map, ensuring all data is combined.
        BinaryOperator<Map<String, DepartmentReportAccumulator>> combiner = (map1, map2) -> {
            map2.forEach((deptName, acc2) ->
                // For each department in map2, merge its accumulator into map1.
                // If map1 already has the department, combine the two accumulators.
                // If not, put acc2 directly into map1.
                map1.merge(deptName, acc2, (acc1, accCombined) -> acc1.combine(accCombined))
            );
            return map1; // Return the merged map (one of the original maps mutated)
        };

        // Finisher: Performs the final transformation on the accumulated data.
        // It converts the Map<String, DepartmentReportAccumulator> into
        // Map<String, DepartmentReport> by calling toDepartmentReport() on each accumulator.
        Function<Map<String, DepartmentReportAccumulator>, Map<String, Department>> finisher = accMap -> {
            Map<String, Department> finalReportMap = new HashMap<>();
            accMap.forEach((deptName, acc) ->
                finalReportMap.put(deptName, acc.toDepartmentReport()) // Convert accumulator to final report
            );
            return finalReportMap;
        };

        // Characteristics:
        // IDENTITY_FINISH: Indicates that the finisher function is an identity function
        // (i.e., the result container is the final form). This is NOT true for us as we
        // transform `Map<String, DepartmentReportAccumulator>` to `Map<String, DepartmentReport>`.
        // So, we do NOT include IDENTITY_FINISH.
        // CONCURRENT: Indicates that the accumulator can be called concurrently on the same result container
        // if the collector is CONCURRENT. Our `HashMap` and `DepartmentReportAccumulator` are not
        // inherently thread-safe for direct concurrent modification of the same instance.
        // However, the `combiner` makes it suitable for parallel processing where intermediate maps
        // are combined. For true CONCURRENT, the `accumulator` for `accMap.computeIfAbsent` and `accumulate`
        // would need to be thread-safe (e.g., using `ConcurrentHashMap`).
        // Given the problem implies working with parallel streams and `combine` does the heavy lifting,
        // `CONCURRENT` characteristic isn't strictly needed if the `combiner` handles all merging.
        // However, it's generally good practice to mark collectors with `CONCURRENT` if the internal
        // structure supports parallel accumulation (often relies on `combiner` for correctness).
        // For simplicity and correctness with the provided `combiner`, we omit `CONCURRENT`
        // to avoid incorrect assumptions by the Stream API about the `accumulator`'s thread-safety for `accMap`.
        // Our accumulator on `Map` could cause race conditions without `ConcurrentHashMap`.
        // A `toMap` collector with merge function is effectively doing what our `supplier`, `accumulator`,
        // `combiner` on a `Map` is doing, and that requires `ConcurrentHashMap` for CONCURRENT.

        // For this specific setup (Map as accumulator, then merging and transforming), usually
        // no characteristics are explicitly needed since the default behavior is sufficient
        // and the combiner ensures parallel correctness. If `finisher` were truly identity, we'd add it.
        // If we used a `ConcurrentHashMap` as the supplier, we could add `CONCURRENT`.
        return Collector.of(
            supplier,
            accumulator,
            combiner,
            finisher,
            Collector.Characteristics.UNORDERED // The order of departments in the map doesn't matter
            // No IDENTITY_FINISH because we transform Map<String, Acc> to Map<String, Report>
        );
    }
}