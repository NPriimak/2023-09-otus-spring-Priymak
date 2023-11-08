package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        final var resource = getQuestionsResource();
        return readQuestionsFromResource(resource);
    }

    /**
     * Чтение из ресурса с вопросами
     *
     * @param resource ресурс с вопросами
     * @return список с вопросами
     */
    private List<Question> readQuestionsFromResource(Resource resource) {
        try (final var is = resource.getInputStream();
             final var isr = new InputStreamReader(is)) {
            return new CsvToBeanBuilder<QuestionDto>(isr)
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (Exception e) {
            throw new QuestionReadException("Exception in process of reading questions", e);
        }
    }

    /**
     * Получение ресурса с вопросами
     *
     * @return ресурс файла с вопросами
     */
    private Resource getQuestionsResource() {
        final var fileName = fileNameProvider.getTestFileName();
        checkFileExt(fileName);
        final var resource = new ClassPathResource(fileName);
        checkResource(resource);
        return resource;
    }

    /**
     * Проверка ресурса на существование и досутпность
     *
     * @param resource ресурс
     */
    private void checkResource(Resource resource) {
        if (!resource.exists()) {
            throw new QuestionReadException("File is not exist. File name: " + resource.getFilename());
        }

        if (!resource.isReadable()) {
            throw new QuestionReadException("File is not readable. File name: " + resource.getFilename());
        }
    }

    /**
     * Проверка расширения файла
     *
     * @param fileName имя файла
     */
    private void checkFileExt(String fileName) {
        if (!fileName.endsWith(".csv")) {
            throw new QuestionReadException("File must have .csv format");
        }
    }
}
