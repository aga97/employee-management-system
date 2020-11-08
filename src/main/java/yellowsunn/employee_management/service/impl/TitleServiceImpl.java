package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.dto.TitleDto;
import yellowsunn.employee_management.repository.TitleRepository;
import yellowsunn.employee_management.service.TitleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    @Override
    @Transactional(readOnly = true)
    public TitleDto.All findAll(Sort sort) {
        List<String> titles = titleRepository.findTitles(sort);

        List<TitleDto.All.Content> content = titles.stream()
                .map(TitleDto.All.Content::new)
                .collect(Collectors.toList());

        return TitleDto.All.builder()
                 .content(content)
                 .size(titles.size())
                 .build();
    }
}
