package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.model.RAM
import com.example.buyit.repositories.RAMRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RAMService (
    private val ramRepository: RAMRepository
) {

    public fun getRamRecommendations(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        when (filterObject.pcUseCase) {
            "work" -> {
                return getRamReccomendationsWork(ramBudget, filterObject)
            }

            else -> {
                return listOf()
            }
        }
    }

    private fun getRamReccomendationsWork(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        val ramQueryObject = RAM();

        ramQueryObject.priceUSD = ramBudget
        ramQueryObject.capacity = 4
        ramQueryObject.type = "DDR3"
        ramQueryObject.frequency = 1600
        ramQueryObject.modules = "1 x"

        if(filterObject.pcWorkMultitask == true) {
            ramQueryObject.capacity = 8
            ramQueryObject.type = "DDR4"
            ramQueryObject.frequency = 2400
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcWorkProgramming == true || filterObject.pcWorkVirtualization == true) {
            ramQueryObject.capacity = 16
            ramQueryObject.type = "DDR4"
            ramQueryObject.frequency = 3200
            ramQueryObject.modules = "2 x"
        }

        val queryResult = this.queryRAMCollection(ramQueryObject, RAMQueryType.QUIZ_WORK)

        if(queryResult.isEmpty()) {
            ramQueryObject.capacity = 4
            ramQueryObject.type = "DDR3"
            ramQueryObject.frequency = 1600
            ramQueryObject.modules = "1 x"

            return this.queryRAMCollection(ramQueryObject, RAMQueryType.QUIZ_WORK)
        }

        return queryResult
    }


    public fun queryRAMCollection(ramFilterObject: RAM, ramQueryType: RAMQueryType): List<RAM> {
        val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "capacity"))

        when(ramQueryType) {
            RAMQueryType.QUIZ_WORK -> {
                return this.ramRepository.findByCapacityGreaterThanEqualAndTypeGreaterThanEqualAndFrequencyGreaterThanEqualAndModulesContainingAndPriceUSDLessThanEqual(
                    ramFilterObject.capacity, ramFilterObject.type, ramFilterObject.frequency, ramFilterObject.modules, ramFilterObject.priceUSD, pageRequest
                )
            }

            else -> {
                return listOf()
            }
        }
    }
}