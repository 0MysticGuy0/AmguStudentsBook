package com.mygy.studentbook.Data;

import com.google.firebase.Timestamp;
import com.mygy.studentbook.Data.Utilites.Constants;
import com.mygy.studentbook.Data.Utilites.DataBaseHelper;
import com.mygy.studentbook.Data.Utilites.DatabaseActionListener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NoteCreator {
    public static Note createNote(String mark, Student student, Subject subject, int subjectNumber, Date date , DatabaseActionListener actionListener){
        Note note;
        List<Note> notes = student.getNotes().stream()
                .filter(nt -> nt.getDate().equals(date) && nt.getSubject()==subject && nt.getSubjectNumber() == subjectNumber)
                .collect(Collectors.toList());
        if(notes.size() == 0){
            note = new Note(mark,student,subject,subjectNumber,date);
            Note finalNote = note;
            DataBaseHelper.addNoteToBase(note, success -> {
                DataBaseHelper.updateNoteDataInBase(finalNote,actionListener);
            });
        }else{
            note = notes.get(0);
        }

        return note;
    }

    public static void deleteNote(Note note){
        DataBaseHelper.removeNoteFromBase(note,Constants.IgnoringDatabaseActionListener);
    }

    public static Note retrieveNoteFromDoc(HashMap<String, Object> doc){
        Note note = null;
        try{
            String id = (String) doc.get(Constants.NOTE_ID);
            String mark = (String) doc.get(Constants.NOTE_MARK);
            Date date = ((Timestamp) doc.get(Constants.NOTE_DATE)).toDate();
            int subjectNumber = ((Long) doc.get(Constants.NOTE_SUBJECT_NUMBER)).intValue();

            String studentId = (String) doc.get(Constants.NOTE_STUDENT_ID);
            Student student = Student.getStudentById(studentId);
            String subjectId = (String) doc.get(Constants.NOTE_SUBJECT_ID);
            Subject subject = Subject.getSubjectById(subjectId);

            if(student == null || subject == null)
                return null;
            note = new Note(mark,student,subject,subjectNumber,date);
            note.setId(id);

        }catch (Exception ex){
            return  null;
        }
        return note;
    }
}
