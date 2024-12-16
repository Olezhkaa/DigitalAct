package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.TaskEntity
import com.example.digitalact.Domains.Model.Task

class TaskMapper : EntityMapper<TaskEntity, Task> {
    override fun mapToEntity(domainModel: Task): TaskEntity = TaskEntity(
        id = 0,
        accountNumber = domainModel.accountNumber,
        address = domainModel.address,
        fullName = domainModel.fullName,
        typePU = domainModel.typePU,
        numberPU = domainModel.numberPU,
        reasonReplacement = domainModel.reasonReplacement,
        numberApplication = domainModel.numberApplication,
        telephone = domainModel.telephone,
        comment = domainModel.comment,
    )

    override fun mapFromEntity(entity: TaskEntity): Task = Task(
        accountNumber = entity.accountNumber,
        address = entity.address,
        fullName = entity.fullName,
        typePU = entity.typePU,
        numberPU = entity.numberPU,
        reasonReplacement = entity.reasonReplacement,
        numberApplication = entity.numberApplication,
        telephone = entity.telephone,
        comment = entity.comment,
    )
}