-- Copyright (c) 2014 Sacred Scripture Foundation.
-- "All scripture is given by inspiration of God, and is profitable for
-- doctrine, for reproof, for correction, for instruction in righteousness:
-- That the man of God may be perfect, throughly furnished unto all good
-- works." (2 Tim 3:16-17)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

CREATE USER 'appbatch'@'localhost' IDENTIFIED BY 'password1';

GRANT SELECT, INSERT, UPDATE, DELETE 
    ON sacredscripture.* TO 'appbatch'@'localhost';
