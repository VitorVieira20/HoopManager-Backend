package com.hoopmanger.api.repositories;

import com.hoopmanger.api.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club, UUID> {

    @Query( "SELECT c FROM Club c WHERE c.id = :clubId" )
    Club findClubById( @Param( "clubId" ) UUID clubId );

    @Query( "SELECT c FROM Club c WHERE c.owner_id = :ownerId ORDER BY c.name" )
    List<Club> findClubsByOwnerId( @Param( "ownerId" ) UUID ownerId );

    @Query( "SELECT c FROM Club c WHERE c.name ILIKE %:clubName%" )
    List<Club> findClubsByName( @Param( "clubName" ) String clubName );

    @Query( "SELECT c FROM Club c WHERE c.id IN :clubIds" )
    List<Club> findUserFavoriteClubsByIds( @Param( "clubIds" ) List<UUID> clubIds );
}
