package org.example.serialization.benchmark;

import java.util.ArrayList;
import java.util.List;

import static org.example.serialization.benchmark.helper.MockDataProducer.generateRandomBackPackWeightInKilogram;
import static org.example.serialization.benchmark.helper.MockDataProducer.generateRandomSuitcaseWeightInKilogram;

public class MockBelongingFactory implements MockFactory<Integer, List<Belonging>> {

    @Override
    public List<Belonging> generate(Integer round) {
        int belongingCount = getBelongingCount(round);
        List<Belonging> belongings = new ArrayList<>();
        for (int id = 1; id <= belongingCount; id++) {
            belongings.add(generateBelonging(id));
        }
        return belongings;
    }

    private int getBelongingCount(Integer round) {
        return round / 2 == 0 ? 2 : 1;
    }

    private Belonging generateBelonging(Integer id) {
        final Belonging.BelongingType belongingType = generateType(id);
        final float weightInKilogram = generateWeight(belongingType);
        return Belonging.builder()
                .id(id)
                .type(belongingType)
                .weightInKilogram(weightInKilogram)
                .build();
    }

    private float generateWeight(Belonging.BelongingType belongingType) {
        if (belongingType == Belonging.BelongingType.BACKPACK) {
            return generateRandomBackPackWeightInKilogram();
        } else {
            return generateRandomSuitcaseWeightInKilogram();
        }
    }

    private Belonging.BelongingType generateType(Integer id) {
        return id == 1 ? Belonging.BelongingType.BACKPACK : Belonging.BelongingType.SUITCASE;
    }
}
