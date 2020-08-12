package ir.mahmood.sahame.service;

import ir.mahmood.sahame.dto.StockDto;
import ir.mahmood.sahame.entity.StockEntity;
import ir.mahmood.sahame.repository.StockRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private StockRepository stockRepository;
    private ModelMapper modelMapper;

    @Autowired
    public StockService(StockRepository stockRepository, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    public void store(StockDto stockDto) {
        stockRepository.save(modelMapper.map(stockDto, StockEntity.class));
    }

    public void bulkStore(List<StockDto> stockDtos) {
        List<StockEntity> stockEntities = stockDtos.stream().map(
                stockDto -> modelMapper.map(stockDto, StockEntity.class)
        ).collect(Collectors.toList());

        stockRepository.saveAll(stockEntities);
    }

    public List<StockDto> list() {
        return stockRepository.findAll().stream().map(
                stockEntity -> modelMapper.map(stockEntity, StockDto.class)
        ).collect(Collectors.toList());
    }

    public Page<StockDto> list(Pageable pageable) {
        Page<StockEntity> stockEntities = stockRepository.findAll(pageable);

        return stockEntities.map(stockEntity -> modelMapper.map(stockEntity, StockDto.class));
    }
}
