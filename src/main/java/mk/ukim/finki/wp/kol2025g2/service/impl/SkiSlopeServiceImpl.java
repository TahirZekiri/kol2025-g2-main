package mk.ukim.finki.wp.kol2025g2.service.impl;

import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiSlopeIdException;
import mk.ukim.finki.wp.kol2025g2.repository.SkiSlopeRepository;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SkiSlopeServiceImpl implements SkiSlopeService {

    private final SkiSlopeRepository skiSlopeRepository;
    private final SkiResortService skiResortService;

    public SkiSlopeServiceImpl(SkiSlopeRepository skiSlopeRepository, SkiResortService skiResortService) {
        this.skiSlopeRepository = skiSlopeRepository;
        this.skiResortService = skiResortService;
    }

    @Override
    public List<SkiSlope> listAll() {
        return this.skiSlopeRepository.findAll();
    }

    @Override
    public SkiSlope findById(Long id) {
        return this.skiSlopeRepository.findById(id)
                .orElseThrow(() -> new InvalidSkiSlopeIdException(id));
    }

    @Override
    public SkiSlope create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        SkiResort resort = this.skiResortService.findById(skiResort);
        SkiSlope skiSlope = new SkiSlope(name, length, difficulty, resort);
        return this.skiSlopeRepository.save(skiSlope);
    }

    @Override
    public SkiSlope update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        SkiSlope skiSlope = this.findById(id);
        SkiResort resort = this.skiResortService.findById(skiResort);

        skiSlope.setName(name);
        skiSlope.setLength(length);
        skiSlope.setDifficulty(difficulty);
        skiSlope.setSkiResort(resort);
        return this.skiSlopeRepository.save(skiSlope);
    }

    @Override
    public SkiSlope delete(Long id) {
        SkiSlope skiSlope = this.findById(id);
        this.skiSlopeRepository.delete(skiSlope);
        return skiSlope;
    }

    @Override
    public SkiSlope close(Long id) {
        SkiSlope skiSlope = this.findById(id);
        skiSlope.setClosed(true);
        return this.skiSlopeRepository.save(skiSlope);
    }

    @Override
    public Page<SkiSlope> findPage(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, int pageNum, int pageSize) {
        List<SkiSlope> filtered = this.skiSlopeRepository.findAll()
                .stream()
                .filter(s -> name == null || name.isEmpty() || s.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(s -> length == null || s.getLength() > length)
                .filter(s -> difficulty == null || s.getDifficulty() == difficulty)
                .filter(s -> skiResort == null || (s.getSkiResort() != null && Objects.equals(s.getSkiResort().getId(), skiResort)))
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(Math.max(pageNum, 0), Math.max(pageSize, 1));
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<SkiSlope> pageContent = start > filtered.size() ? List.of() : filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }
}
