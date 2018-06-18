/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix-delete-dialog.component';
import { AnomaliaMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.service';

describe('Component Tests', () => {

    describe('AnomaliaMySuffix Management Delete Component', () => {
        let comp: AnomaliaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<AnomaliaMySuffixDeleteDialogComponent>;
        let service: AnomaliaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMySuffixDeleteDialogComponent],
                providers: [
                    AnomaliaMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
