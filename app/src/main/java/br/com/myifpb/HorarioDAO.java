package br.com.myifpb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HorarioDAO {
    @Insert
    public void insert(Horario... horarios);

    @Query("DELETE FROM horario")
    public void delete();

    @Query("SELECT * FROM horario")
    public List<Horario> list();

    @Query("SELECT * FROM horario WHERE idHorario == 1")
    public Horario query();

}
