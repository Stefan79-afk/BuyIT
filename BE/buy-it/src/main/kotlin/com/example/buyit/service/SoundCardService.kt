package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.model.SoundCard
import com.example.buyit.repositories.SoundCardRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SoundCardService (
    private val soundCardRepository: SoundCardRepository
){

    fun getSoundCardRecommendations(soundCardBudget: Double, filterObject: PCRequest): List<SoundCard> {
        val soundCardQueryObject = SoundCard()

        soundCardQueryObject.priceUSD = soundCardBudget

        when (filterObject.pcSoundChannel) {
            "2.0" -> {
                soundCardQueryObject.channels = "2.0"
            }

            "4.0" -> {
                soundCardQueryObject.channels = "4.0"
            }

            "5.1" -> {
                soundCardQueryObject.channels = "5.1"
            }

            "7.1" -> {
                soundCardQueryObject.channels = "7.1"
            }
        }

        val queryResult = querySoundCardCollection(soundCardQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            soundCardQueryObject.channels = ""
            return querySoundCardCollection(soundCardQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun querySoundCardCollection(soundCardQueryObject: SoundCard, queryType: QueryType): List<SoundCard> {
        return when (queryType) {
            QueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(soundCardQueryObject.channels == "") {
                    this.soundCardRepository.findByPriceUSDLessThanEqual(soundCardQueryObject.priceUSD, pageRequest)
                } else {
                    this.soundCardRepository.findByChannelsAndPriceUSDLessThanEqual(soundCardQueryObject.channels, soundCardQueryObject.priceUSD, pageRequest)
                }
            }

            else -> listOf()
        }
    }
}