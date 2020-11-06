package ner

sealed trait NERType {
  def serialized: String
}

object NERTypes {
  def deserialized(string: String): NERType = string match {
    case NERLocations.`serialized` => NERLocations
    case NEROrganisations.`serialized` => NEROrganisations
    case NERPersons.`serialized` => NERPersons
    case NERDate.`serialized` => NERDate
    case _ => Unknown
  }

  object NERLocations extends NERType {
    override val serialized: String = "Locations"
  }

  object NEROrganisations extends NERType {
    override val serialized: String = "Organisations"
  }

  object NERPersons extends NERType {
    override val serialized: String = "Persons"
  }

  object NERDate extends NERType {
    override val serialized: String = "Date"
  }

  object Unknown extends NERType {
    override val serialized: String = "Unknown"
  }
}
