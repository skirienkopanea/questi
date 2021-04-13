## Lecture entity 
1. l_id_idx (indexed by default `id` and **has** unqiue constraint)
2. l_mlink_idx (indexed by `moderator_link` and **has** unique constraint)
3. l_plink_idx (indexed by `public_link` and **has** unique constraint)

## User entity 

1. u_id_idx (indexed by default `id` and **has** unique constraint)
2. u_lecture_id_idx ( indexed by `lecture_id` and **has no** unique constraint, many users can have the same lecture_id)

## Question entity 

1. qid_idx (indexed by default `id` and **has** unique constraint)
2. q_lecture_id_idx (indexed by `lecture_id` and **has no** unique constraint, many questions in one lecture)
3. q_user_id_idx (indexed by `user_id` and **has no** unique constraint, many questions can have the same user)


**Explanation**: Only entities/queries that are expected to be used often have indexes. The other ones are omitted at the moment. We can always add more if necessary. 