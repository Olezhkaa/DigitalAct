package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Domains.Model.Act

class ActMapper: EntityMapper<ActEntity, Act> {
    override fun mapToEntity(domainModel: Act): ActEntity = ActEntity(
        id = 0,
        accountNumber = domainModel.accountNumber,
        address = domainModel.address,
        fullName = domainModel.fullName,
        typeOldPU = domainModel.typeOldPU,
        numberOldPU = domainModel.numberOldPU,
        typeOldTT = domainModel.typeOldTT,
        numberOldTT = domainModel.numberOldTT,
        reasonReplacement = domainModel.reasonReplacement,
        numberApplication = domainModel.numberApplication,
        typeNewPU = domainModel.typeNewPU,
        numberNewPU = domainModel.numberNewPU,
        typeNewTT = domainModel.typeNewTT,
        numberNewTT = domainModel.numberNewTT,
        sealPUOne = domainModel.sealPUOne,
        sealPUTwo = domainModel.sealPUTwo,
        sealPUThree = domainModel.sealPUThree,
        simCard = domainModel.simCard,
        dateCompletion = domainModel.dateCompletion,
        fullNameExecutor = domainModel.fullNameExecutor,
        installationLocation = domainModel.installationLocation,
        comment = domainModel.comment,
    )

    override fun mapFromEntity(entity: ActEntity): Act = Act(
        accountNumber = entity.accountNumber,
        address = entity.address,
        fullName = entity.fullName,
        typeOldPU = entity.typeOldPU,
        numberOldPU = entity.numberOldPU,
        typeOldTT = entity.typeOldTT,
        numberOldTT = entity.numberOldTT,
        reasonReplacement = entity.reasonReplacement,
        numberApplication = entity.numberApplication,
        typeNewPU = entity.typeNewPU,
        numberNewPU = entity.numberNewPU,
        typeNewTT = entity.typeNewTT,
        numberNewTT = entity.numberNewTT,
        sealPUOne = entity.sealPUOne,
        sealPUTwo = entity.sealPUTwo,
        sealPUThree = entity.sealPUThree,
        simCard = entity.simCard,
        dateCompletion = entity.dateCompletion,
        fullNameExecutor = entity.fullNameExecutor,
        installationLocation = entity.installationLocation,
        comment = entity.comment,
    )


}