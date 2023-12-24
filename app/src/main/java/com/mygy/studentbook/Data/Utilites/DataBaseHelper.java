package com.mygy.studentbook.Data.Utilites;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mygy.studentbook.Data.Note;
import com.mygy.studentbook.Data.NoteCreator;
import com.mygy.studentbook.Data.StudentGroup;
import com.mygy.studentbook.Data.StudentGroupCreator;
import com.mygy.studentbook.Data.Subject;
import com.mygy.studentbook.Data.SubjectCreator;
import com.mygy.studentbook.Data.User;
import com.mygy.studentbook.Data.UserCreator;

import java.util.HashMap;

public abstract class DataBaseHelper {

    private static FirebaseFirestore fireStore;

    static{
        fireStore = FirebaseFirestore.getInstance();
    }

    public static void addUserToBase(User user, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.USERS_BASE)
                .add(user.getUserDoc())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        user.setId(documentReference.getId());
                        System.out.println("\n================\nПользователь успешно создан\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void removeUserFromBase(User user,DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.USERS_BASE)
                .document(user.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nПользователь удален\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        System.out.println("=================\nERROR deleting "+user.getId()+"from base\n============");
                    }
                });
    }
    public static void updateUserDataInBase(User user,DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.USERS_BASE)
                .document(user.getId())
                .update(user.getUserDoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nДанные пользователя обновлены\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void addGroupToBase(StudentGroup group, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.GROUPS_BASE)
                .add(group.getGroupDoc())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        group.setId(documentReference.getId());
                        System.out.println("\n================\nГруппа успешно создана\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void updateGroupDataInBase(StudentGroup group,DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.GROUPS_BASE)
                .document(group.getId())
                .update(group.getGroupDoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nДанные группы обновлены\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void addSubjectToBase(Subject subject, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.SUBJECTS_BASE)
                .add(subject.getSubjectDoc())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        subject.setId(documentReference.getId());
                        System.out.println("\n================\nПредмет успешно создан\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void updateSubjectDataInBase(Subject subject,DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.SUBJECTS_BASE)
                .document(subject.getId())
                .update(subject.getSubjectDoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nДанные предмета обновлены\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void addNoteToBase(Note note, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.NOTES_BASE)
                .add(note.getNoteDoc())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        note.setId(documentReference.getId());
                        System.out.println("\n================\nЗапись успешно создана\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void removeNoteFromBase(Note note, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.NOTES_BASE)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nЗапись "+note.getId()+" удалена\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        System.out.println("=================\nERROR deleting note "+note.getId()+" from base\n============");
                    }
                });
    }
    public static void updateNoteDataInBase(Note note, DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.NOTES_BASE)
                .document(note.getId())
                .update(note.getNoteDoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("\n================\nДанные записи обновлены\n==============\n");
                        databaseActionListener.onActionCompleted(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    ///////////////////////

    public static void getAllUsersFromBase(DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.USERS_BASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){

                            for(DocumentSnapshot d:task.getResult()){
                                HashMap<String, Object> userDoc = ( HashMap<String, Object>) d.getData();
                                UserCreator.retrieveUserFromDoc(userDoc);
                            }

                            databaseActionListener.onActionCompleted(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void getAllGroupsFromBase(DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.GROUPS_BASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){

                            for(DocumentSnapshot d:task.getResult()){
                                HashMap<String, Object> groupDoc = ( HashMap<String, Object>) d.getData();
                                StudentGroupCreator.retrieveGroupFromDoc(groupDoc);
                            }

                            databaseActionListener.onActionCompleted(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void getAllSubjectsFromBase(DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.SUBJECTS_BASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){

                            for(DocumentSnapshot d:task.getResult()){
                                HashMap<String, Object> subjDoc = ( HashMap<String, Object>) d.getData();
                                SubjectCreator.retrieveSubjectFromDoc(subjDoc);
                            }

                            databaseActionListener.onActionCompleted(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
    public static void getAllNotesFromBase(DatabaseActionListener databaseActionListener){
        fireStore.collection(Constants.NOTES_BASE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){

                            for(DocumentSnapshot d:task.getResult()){
                                HashMap<String, Object> noteDoc = ( HashMap<String, Object>) d.getData();
                                NoteCreator.retrieveNoteFromDoc(noteDoc);
                            }

                            databaseActionListener.onActionCompleted(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseActionListener.onActionCompleted(false);
                        throw new RuntimeException(e);
                    }
                });
    }
}
