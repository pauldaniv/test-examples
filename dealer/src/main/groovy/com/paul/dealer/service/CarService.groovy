package com.paul.dealer.service

import com.paul.dealer.persintence.CarRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CarService {

  final CarRepository carRepository

}
