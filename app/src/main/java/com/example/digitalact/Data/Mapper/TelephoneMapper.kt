package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.TelephoneEntity
import com.example.digitalact.Domains.Model.Telephone

class TelephoneMapper : EntityMapper<TelephoneEntity, Telephone> {
    override fun mapFromEntity(entity: TelephoneEntity): Telephone = Telephone(
        numberTelephone = entity.numberTelephone,
        iccId = entity.iccID
    )



    override fun mapToEntity(domainModel: Telephone): TelephoneEntity = TelephoneEntity(
        id = 0,
        numberTelephone = domainModel.numberTelephone,
        iccID = domainModel.iccId
    )
}