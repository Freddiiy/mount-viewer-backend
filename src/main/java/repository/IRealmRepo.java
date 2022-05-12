package repository;

import dtos.RealmDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface IRealmRepo {
    Set<RealmDTO> getAllRealms() throws IOException, URISyntaxException;
    Set<RealmDTO> getAllRealmsOfRegion(String region) throws IOException, URISyntaxException;
    RealmDTO getRealm(String region, int realmId) throws IOException, URISyntaxException;
}
