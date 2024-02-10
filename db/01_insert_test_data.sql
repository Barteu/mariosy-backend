-- Users
insert into user_account (id, external_id, username)
values (1, '059e0b2c-1254-47d7-9fbf-577ead2f36d8', 'John Doe');

insert into user_account (id, external_id, username)
values (2, '8d1a9c5f-6db6-4b68-bf57-4754ddeda7d4', 'Jack Jackson');

insert into user_account (id, external_id, username)
values (3, '4e8780e0-884c-491d-89c2-f4c29ba5c0fd', 'Amelia Amelson');

insert into user_account (id, external_id, username)
values (4,'444644eb-8385-46a3-9b3a-529fd26d3444', 'Oliwia Italiano');

-- Marios 1
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (1, 'Great job my friend! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '10 hour', 'f10cc1cb-a654-404c-88d3-cb86b0d35d1b', 'Great job!',1, 1);

insert into marios_receiver (marios_id, user_id)
values (1,3);

-- Marios 2
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (2, 'Thank You. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '9 hour', 'f20cc1cb-a654-404c-88d3-cb86b0d35d2b', 'Thank You!', 0, 1);

insert into marios_receiver (marios_id, user_id)
values (2,3);

-- Marios 3
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (3, 'Very nice. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '8 hour', 'f30cc1cb-a654-404c-88d3-cb86b0d35d3b', 'Nice byq', 5, 1);

insert into marios_receiver (marios_id, user_id)
values (3,2);

-- Marios 4
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (4, 'Eat fresh Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '7 hour', 'f40cc1cb-a654-404c-88d3-cb86b0d35d4b', 'Eat fresh', 4, 1);

insert into marios_receiver (marios_id, user_id)
values (4,4);

-- Marios 5
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (5, 'Good job Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '6 hour', 'f50cc1cb-a654-404c-88d3-cb86b0d35d5b', 'Good job m8', 2, 1);

insert into marios_receiver (marios_id, user_id)
values (5,3);

-- Marios 6
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (6, 'Good jobex Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '5 hour', 'f60cc1cb-a654-404c-88d3-cb86b0d35d6b', 'Greeeeat!!!', 4, 1);

insert into marios_receiver (marios_id, user_id)
values (6,4);

-- Marios 7
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (7, 'Excellent Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '4 hour', 'f70cc1cb-a654-404c-88d3-cb86b0d35d7b', 'Excellent', 3, 1);

insert into marios_receiver (marios_id, user_id)
values (7,2);

-- Marios 8
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (8, 'Nice nice grappa ice! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '3 hour', 'f80cc1cb-a654-404c-88d3-cb86b0d35d8b', 'Nice :))))', 2, 3);

insert into marios_receiver (marios_id, user_id)
values (8,1);

-- Marios 9
insert into marios_entity (id, comment, creation_timestamp, external_id, title, type, ref_creator_id)
values (9, 'He he he Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel rhoncus tellus, ac placerat sem.', CURRENT_TIMESTAMP - interval '2 hour', 'f90cc1cb-a654-404c-88d3-cb86b0d35d9b', 'Hoho!', 2, 3);

insert into marios_receiver (marios_id, user_id)
values (9,4);