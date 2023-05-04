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

        val queryResult = querySoundCardCollection(soundCardQueryObject, SoundCardQueryType.QUIZ)

        if(queryResult.isEmpty()) {
            soundCardQueryObject.channels = ""
            return querySoundCardCollection(soundCardQueryObject, SoundCardQueryType.QUIZ)
        }

        return queryResult
    }

    private fun querySoundCardCollection(soundCardQueryObject: SoundCard, soundCardQueryType: SoundCardQueryType): List<SoundCard> {
        when (soundCardQueryType) {
            SoundCardQueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(soundCardQueryObject.channels == "") {
                    return this.soundCardRepository.findByPriceUSDLessThanEqual(soundCardQueryObject.priceUSD, pageRequest)
                } else {
                    return this.soundCardRepository.findByChannelsAndPriceUSDLessThanEqual(soundCardQueryObject.channels, soundCardQueryObject.priceUSD, pageRequest)
                }
            }
        }
    }
}