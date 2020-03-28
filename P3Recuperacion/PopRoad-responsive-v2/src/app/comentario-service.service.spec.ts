import { TestBed } from '@angular/core/testing';

import { ComentarioServiceService } from './comentario-service.service';

describe('ComentarioServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ComentarioServiceService = TestBed.get(ComentarioServiceService);
    expect(service).toBeTruthy();
  });
});
